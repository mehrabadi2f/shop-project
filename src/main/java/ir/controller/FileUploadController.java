package ir.controller;

import ir.service.FileProcessingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@RestController
@RequestMapping("/upload")
public class FileUploadController {

    private final FileProcessingService fileProcessingService;

    public FileUploadController(FileProcessingService fileProcessingService) {
        this.fileProcessingService = fileProcessingService;
    }


    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<String> uploadCsv(@RequestParam("file") MultipartFile file)
            throws Exception {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }

        try (InputStream inputStream = file.getInputStream()) {
            fileProcessingService.processCsv(inputStream);
            return ResponseEntity.ok("File processed successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error processing file: " + e.getMessage());
        }



    }
}
/*
@RestController
@RequestMapping("/api/files")
public class FileUploadController {

    private final FileStorageService storage;

    public FileUploadController(FileStorageService storage) {
        this.storage = storage;
    }

    // آپلود
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<?> upload(@RequestPart("file") MultipartFile file) {
        try {
            String saved = storage.save(file);
            return ResponseEntity.ok("Uploaded: " + saved);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Upload failed: " + e.getMessage());
        }
    }

    // لیست فایل‌ها
    @GetMapping("/list")
    public ResponseEntity<List<String>> listFiles() {
        try {
            return ResponseEntity.ok(storage.listFiles());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // دانلود
    @GetMapping("/download/{filename}")
    public ResponseEntity<?> download(@PathVariable String filename) {
        try {
            Path file = storage.load(filename);
            if (!Files.exists(file)) {
                return ResponseEntity.notFound().build();
            }

            byte[] data = Files.readAllBytes(file);

            return ResponseEntity
                    .ok()
                    .header("Content-Disposition", "attachment; filename=\"" + filename + "\"")
                    .body(data);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Download failed: " + e.getMessage());
        }
    }

    // حذف
    @DeleteMapping("/delete/{filename}")
    public ResponseEntity<?> delete(@PathVariable String filename) {
        try {
            boolean removed = storage.delete(filename);
            if (removed)
                return ResponseEntity.ok("Deleted: " + filename);
            else
                return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Delete failed: " + e.getMessage());
        }
    }
}
*/