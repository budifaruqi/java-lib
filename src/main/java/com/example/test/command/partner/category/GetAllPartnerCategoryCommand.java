package com.example.test.command.partner.category;

import com.example.test.command.model.partner.category.GetAllPartnerCategoryCommandRequest;
import com.example.test.web.model.response.partner.category.GetPartnerCategoryWebResponse;
import com.solusinegeri.command.reactive.Command;
import org.springframework.data.domain.Page;

public interface GetAllPartnerCategoryCommand
    extends Command<GetAllPartnerCategoryCommandRequest, Page<GetPartnerCategoryWebResponse>> {}
