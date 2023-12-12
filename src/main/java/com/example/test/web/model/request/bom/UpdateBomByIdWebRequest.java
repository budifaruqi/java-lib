package com.example.test.web.model.request.bom;

import com.example.test.common.vo.MaterialVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateBomByIdWebRequest {

  private List<MaterialVO> materialList;
}
