package com.example.test.command.partner.category;

import com.example.test.command.model.partner.category.UpdatePartnerCategoryCommandRequest;
import com.example.test.web.model.response.partner.category.GetPartnerCategoryWebResponse;
import com.solusinegeri.command.reactive.Command;

public interface UpdatePartnerCategoryCommand
    extends Command<UpdatePartnerCategoryCommandRequest, GetPartnerCategoryWebResponse> {}
