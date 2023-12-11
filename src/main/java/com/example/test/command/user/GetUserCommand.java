package com.example.test.command.user;


import com.example.test.web.model.response.user.GetUserWebResponse;
import com.solusinegeri.command.reactive.Command;

import java.util.List;

public interface GetUserCommand extends Command<String, List<GetUserWebResponse>> {
}
