package org.example.messagemanagementsystem.langmodel;

import com.opencsv.CSVReader;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import opennlp.tools.doccat.*;
import opennlp.tools.ml.naivebayes.NaiveBayesTrainer;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.TrainingParameters;
import org.example.messagemanagementsystem.repository.ModelRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@RequiredArgsConstructor
public class NaiveBayesModel {

    private static final String REDIS_MODEL_KEY = "naive_bayes_model";
    private final ModelRepository modelRepository;
    private DoccatModel model;

    @Value("${naivebayes.dataset.filename}")
    private String datasetFilename;

    @PostConstruct
    public void initialize() throws IOException {
        modelRepository.deleteModel(REDIS_MODEL_KEY);
        byte[] serializedModel = modelRepository.getModel(REDIS_MODEL_KEY);

        if (serializedModel != null) {
            try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(serializedModel)) {
                model = new DoccatModel(byteArrayInputStream);
            }
        } else {
            trainModel();
        }
    }

    @SneakyThrows
    private void trainModel() {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(datasetFilename);

        if (inputStream == null) {
            throw new IllegalArgumentException("Файл не найден: " + datasetFilename);
        }

        try (CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            List<String[]> rows = csvReader.readAll();

            ObjectStream<DocumentSample> sampleStream = new ObjectStream<DocumentSample>() {
                private int currentIndex = 0;

                @Override
                public DocumentSample read() throws IOException {
                    if (currentIndex < rows.size()) {
                        String[] row = rows.get(currentIndex++);
                        String text = row[0]; // Текст
                        String label = row[1]; // Метка (1 или 0)

                        // Создаем DocumentSample, где метка - это категория
                        return new DocumentSample(label, new String[]{text});
                    }
                    return null;
                }

                @Override
                public void reset() throws IOException {
                    currentIndex = 0;
                }

                @Override
                public void close() throws IOException {
                    // Нет необходимости закрывать CSVReader здесь
                }
            };

            // Обучаем модель Naive Bayes
            TrainingParameters params = new TrainingParameters();
            params.put(TrainingParameters.ALGORITHM_PARAM, NaiveBayesTrainer.NAIVE_BAYES_VALUE);
            params.put(TrainingParameters.ITERATIONS_PARAM, "1000"); // Увеличиваем число итераций
            // Не указываем CUTOFF_PARAM для полного отключения порога

            model = DocumentCategorizerME.train("ru", sampleStream, params, new DoccatFactory());
        }

        // Сохраняем модель в Redis
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            model.serialize(byteArrayOutputStream);
            modelRepository.saveModel(REDIS_MODEL_KEY, byteArrayOutputStream.toByteArray());
        }
    }


    public String predictSentiment(String text) {
        if (model == null) {
            throw new IllegalStateException("Модель не инициализирована.");
        }
        DocumentCategorizerME categorizer = new DocumentCategorizerME(model);
        double[] outcomes = categorizer.categorize(new String[]{text});
        return categorizer.getBestCategory(outcomes);
    }
}


