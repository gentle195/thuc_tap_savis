package com.fpoly.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LyDoTraHangDTO extends BaseDTO<LyDoTraHangDTO> {

    private String lyDo;

    private Long hinhAnhId;
}
