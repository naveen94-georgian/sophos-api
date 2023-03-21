package com.cgi.sophos.service;

import static com.cgi.sophos.mapper.Mappers.graphUserMapper;

import com.cgi.sophos.client.GraphClientBuilder;
import com.cgi.sophos.dto.AdUserDto;
import com.microsoft.graph.options.HeaderOption;
import com.microsoft.graph.options.Option;
import jakarta.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

@Service
public record GraphService(GraphClientBuilder graphClientBuilder) {

  public AdUserDto myInfo(HttpServletRequest request) {
    var graphClient = graphClientBuilder.getClient(request);
    var user = graphClient.me().buildRequest().get();

    return graphUserMapper.toDTO(user);
  }

  public AdUserDto getInfoById(@NotNull @NotEmpty String userId, HttpServletRequest request) {
    var graphClient = graphClientBuilder.getClient(request);

    var user = graphClient.users(userId).buildRequest().get();

    return graphUserMapper.toDTO(user);
  }

  public AdUserDto getAllDirectory(HttpServletRequest request) {
    var graphClient = graphClientBuilder.getClient(request);
    LinkedList<Option> requestOptions = new LinkedList<Option>();
    requestOptions.add(new HeaderOption("ConsistencyLevel", "eventual"));

    var selectNames =
        Stream.of(AdUserDto.class.getDeclaredFields())
            .map(Field::getName)
            .collect(Collectors.joining(","));

    var user =
        graphClient
            .me()
            .buildRequest(requestOptions)
            .expand(MessageFormat.format("manager($levels=max;$select={0})", selectNames))
            .select(selectNames)
            .get();
    return graphUserMapper.toDTO(user);
  }
}
