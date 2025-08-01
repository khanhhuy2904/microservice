package com.example.service;

import com.example.entity.Product;
import com.example.repository.ProductRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class ExcelImportService {

    @Autowired
    private ProductRepository productRepository;

    public void importExcel(MultipartFile file) throws IOException {
        try (InputStream is = file.getInputStream()) {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(0); // Sheet đầu tiên

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Bỏ qua dòng tiêu đề
                Product product = new Product();
                product.setName(row.getCell(0).getStringCellValue());
                product.setPrice(row.getCell(1).getNumericCellValue());
                product.setQuantity((int) row.getCell(2).getNumericCellValue());

                productRepository.save(product);
            }
        }
    }
}

