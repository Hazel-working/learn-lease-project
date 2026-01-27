package com.atguigu.lease.web.admin.vo.apartment;


import com.atguigu.lease.model.entity.ApartmentInfo;
import com.atguigu.lease.web.admin.vo.graph.GraphVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;


@Schema(description = "公寓信息")
@Data
public class ApartmentSubmitVo extends ApartmentInfo {
    //继承ApartmentInfo实体类的全部属性

    @Schema(description = "公寓配套id")
    private List<Long> facilityInfoIds;

    @Schema(description = "公寓标签id")
    private List<Long> labelIds;

    @Schema(description = "公寓杂费值id")
    private List<Long> feeValueIds;

    @Schema(description = "公寓图片id")
    private List<GraphVo> graphVoList;
    //GraphVo包含图片名称和图片地址

}
