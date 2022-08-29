package br.com.mymoney.cadastrationservice.models.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@NoArgsConstructor
public class ResponsePageDto<T> {
    private int currentPage;
    private int totalPages;
    private long totalElements;
    private List<T> content;

    public ResponsePageDto(Page<T> page) {
        currentPage = page.getNumber();
        totalPages = page.getTotalPages();
        totalElements= page.getTotalElements();
        content = page.getContent();
    }
}
