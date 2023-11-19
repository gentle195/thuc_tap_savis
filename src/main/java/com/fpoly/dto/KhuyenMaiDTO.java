package com.fpoly.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class KhuyenMaiDTO extends BaseDTO<KhuyenMaiDTO> {

    @NotBlank(message = "Tên khuyến mãi không được để trống")
    private String tenKhuyenMai;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Ngày bắt đầu không được để trống")
    private Date ngayBatDau;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Ngày kết thúc không được để trống")
    private Date ngayKetThuc;

    @NotNull(message = "Phần trăm không được để trống")
    @Min(value = 0, message = "Phần trăm không được nhỏ hơn 0")
    @Max(value = 100, message = "Phần trăm không được lớn hơn 100")
    private Integer phanTramGiam;

    @NotNull(message = "Tiền giảm tối đa không được để trống")
    @Min(value = 0, message = "Tiền giảm tối đa không được nhỏ hơn 0")
    private Integer giaTriToiThieu;

    private boolean trangThai;
}
