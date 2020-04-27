package com.fruit.template.email.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Mail {

    @Pattern(regexp = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$", message = "邮箱格式不正确")
    private String to;
    /**
     * 收件人
     */
    private List<String> toArray;

    @NotBlank(message = "标题不能为空")
    private String title;

    @NotBlank(message = "正文不能为空")
    private String content;
    /**
     * 抄送人
     */
    private List<String> csrArray;

    private String msgId;// 消息id

    public void addCsr(String csr) {
        toArray.add(csr);
    }

    public void addSrj(String sjr) {
        csrArray.add(sjr);
    }
}
