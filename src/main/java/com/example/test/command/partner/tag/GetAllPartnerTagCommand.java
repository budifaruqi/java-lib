package com.example.test.command.partner.tag;

import com.example.test.command.model.partner.tag.GetAllPartnerTagCommandRequest;
import com.example.test.web.model.response.partner.tag.GetPartnerTagWebResponse;
import com.solusinegeri.command.reactive.Command;
import org.springframework.data.domain.Page;

public interface GetAllPartnerTagCommand
    extends Command<GetAllPartnerTagCommandRequest, Page<GetPartnerTagWebResponse>> {}
