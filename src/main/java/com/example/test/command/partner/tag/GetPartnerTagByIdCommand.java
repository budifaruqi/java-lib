package com.example.test.command.partner.tag;

import com.example.test.web.model.response.partner.tag.GetPartnerTagWebResponse;
import com.solusinegeri.command.reactive.Command;

public interface GetPartnerTagByIdCommand extends Command<String, GetPartnerTagWebResponse> {}
