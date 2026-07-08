package ir.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileStorageService {

    private final Path rootLocation;

    public FileStorageService(@Value("${file.upload-dir}") String uploadDir) {
        this.rootLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        createDirectoryIfNeeded();
    }

    private void createDirectoryIfNeeded() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Cannot create upload directory", e);
        }
    }

    // جلوگیری از Path Traversal
    private String sanitizeFilename(String filename) {
        return Paths.get(filename).getFileName().toString();
    }

    // ذخیره‌سازی
    public String save(MultipartFile file) throws IOException {
        String safeName = sanitizeFilename(file.getOriginalFilename());

        Path target = rootLocation.resolve(safeName);

        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

        return safeName;
    }

    // لیست فایل‌ها
    public List<String> listFiles() throws IOException {
        try (var stream = Files.list(rootLocation)) {
            return stream
                    .filter(Files::isRegularFile)
                    .map(path -> path.getFileName().toString())
                    .collect(Collectors.toList());
        }
    }

    // دانلود فایل
    public Path load(String filename) {
        return rootLocation.resolve(sanitizeFilename(filename));
    }

    // حذف فایل
    public boolean delete(String filename) throws IOException {
        Path file = load(filename);
        return Files.deleteIfExists(file);
    }
}
