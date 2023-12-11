package com.example.test.command.brand;

import com.example.test.command.model.brand.GetAllBrandCommandRequest;
import com.example.test.web.model.response.brand.GetBrandWebResponse;
import com.solusinegeri.command.reactive.Command;
import org.springframework.data.domain.Page;

public interface GetAllBrandCommand extends Command<GetAllBrandCommandRequest, Page<GetBrandWebResponse>> {}
