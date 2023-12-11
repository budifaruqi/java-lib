package com.example.test.command.brand;

import com.example.test.web.model.response.brand.GetBrandWebResponse;
import com.solusinegeri.command.reactive.Command;

public interface GetBrandByIdCommand extends Command<String, GetBrandWebResponse> {}
