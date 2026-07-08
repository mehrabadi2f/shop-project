package ir.service;

import ir.dto.HouseholdInput;
import ir.mapper.HouseholdMapper;
import ir.model.Household;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/*
@Service
public class FileProcessingService {

    private static final int BATCH_SIZE = 2000;

    private final HouseholdBatchService batchService;
    private final ValidationService validationService;
    private final HouseholdMapper mapper;

    public FileProcessingService(
            HouseholdBatchService batchService,
            ValidationService validationService,
            HouseholdMapper mapper) {

        this.batchService = batchService;
        this.validationService = validationService;
        this.mapper = mapper;
    }

    public void processCsv(InputStream inputStream) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        List<Household> batch = new ArrayList<>(BATCH_SIZE);

        String line;

        while ((line = reader.readLine()) != null) {

            HouseholdInput dto = parser.parseLine(line);

            validationService.validate(dto);

            Household entity = mapper.toEntity(dto);

            batch.add(entity);

            if (batch.size() >= BATCH_SIZE) {

                batchService.saveBatch(batch);

                batch.clear();
            }
        }

        if (!batch.isEmpty()) {
            batchService.saveBatch(batch);
        }
    }
}
*/

@Service
public class FileProcessingService {

    private static final int BATCH_SIZE = 2000;

    private final HouseholdBatchService batchService;
    private final ValidationService validationService;
    private final HouseholdMapper mapper;

    public FileProcessingService(
            HouseholdBatchService batchService,
            ValidationService validationService,
            HouseholdMapper mapper) {

        this.batchService = batchService;
        this.validationService = validationService;
        this.mapper = mapper;
    }

    public void processCsv(InputStream inputStream) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        // اگر header دارد این خط را فعال کنید
        // reader.readLine();

        List<Household> batch = new ArrayList<>(BATCH_SIZE);

        String line;

        while ((line = reader.readLine()) != null) {

            HouseholdInput dto = parseLine(line);

            validationService.validateRecord(dto);

            Household entity = mapper.toEntity(dto);

            batch.add(entity);

            if (batch.size() >= BATCH_SIZE) {
                batchService.saveBatch(batch);
                batch.clear();
            }
        }

        if (!batch.isEmpty()) {
            batchService.saveBatch(batch);
        }
    }

    private HouseholdInput parseLine(String line) {

        if (line == null || line.trim().isEmpty()) {
            throw new IllegalArgumentException("Empty line in CSV file");
        }

        String[] parts = line.split(",");

        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid CSV line: " + line);
        }

        String nationalId = parts[0].trim();
        String householdSizeStr = parts[1].trim();

        int householdSize;

        try {
            householdSize = Integer.parseInt(householdSizeStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid household size: " + householdSizeStr);
        }

        HouseholdInput dto = new HouseholdInput();
        dto.setNationalId(nationalId);
        dto.setHouseholdSize(householdSize);

        return dto;
    }
}
