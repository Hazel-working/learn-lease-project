package com.atguigu.lease.web.admin.vo.login;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Schema(description = "图像验证码")
@AllArgsConstructor
public class CaptchaVo {

    @Schema(description = "验证码图片信息")
    private String image; // 二进制 -> base64编码后的图像字符串src

    @Schema(description = "验证码key")
    private String key;
}
