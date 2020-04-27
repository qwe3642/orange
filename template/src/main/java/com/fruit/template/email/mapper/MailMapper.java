package com.fruit.template.email.mapper;


import com.fruit.template.email.dto.MailDto;

import java.util.List;

public interface MailMapper {

    public List<MailDto> queryByList();
}
