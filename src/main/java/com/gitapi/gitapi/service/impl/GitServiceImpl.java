package com.gitapi.gitapi.service.impl;

import com.gitapi.gitapi.dto.RepoDto;
import com.gitapi.gitapi.dto.BranchDto;
import com.gitapi.gitapi.dto.GitMasterDto;
import com.gitapi.gitapi.dto.GitDto;
import com.gitapi.gitapi.dto.RateLimitDto;
import com.gitapi.gitapi.exception.git.GitNotFoundException;
import com.gitapi.gitapi.exception.git.GitUnauthorizedException;
import com.gitapi.gitapi.service.GitService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class GitServiceImpl implements GitService {
    private static final String GIT_API_URL = "https://api.github.com";

    private final WebClient webClient;

    public GitServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Mono<RateLimitDto> getLimit() {
        String apiUrl = GIT_API_URL + "/rate_limit";
        try {
            return webClient.get()
                    .uri(apiUrl)
                    .retrieve()
                    .bodyToMono(RateLimitDto.class);
        } catch (HttpClientErrorException.Unauthorized unauthorized) {
            throw new GitUnauthorizedException("User unauthorized!");
        }
    }

    @Override
    public Mono<GitMasterDto> getRepositories(String username) {
        String apiUrl = GIT_API_URL + "/users/" + username + "/repos";
        try {
            return webClient.get()
                    .uri(apiUrl)
                    .retrieve()
                    .bodyToFlux(RepoDto.class)
                    .flatMap(repoDto ->
                            getBranchesForRepository(username, repoDto.getName())
                                    .collectList()
                                    .map(branches ->
                                            GitDto.builder()
                                                    .repoName(repoDto.getName())
                                                    .branches(branches)
                                                    .build()))
                    .collectList()
                    .map(repos ->
                            GitMasterDto.builder()
                                    .userName(username)
                                    .repositories(repos)
                                    .build());

        } catch (HttpClientErrorException.Unauthorized unauthorized) {
            throw new GitUnauthorizedException("You are unauthorized!");
        } catch (HttpClientErrorException.NotFound notFound) {
            throw new GitNotFoundException("Git user not found!");
        }
    }

    @Override
    public Flux<BranchDto> getBranchesForRepository(String userName, String repoName) {
        String apiUrl = GIT_API_URL + "/repos/" + userName + "/" + repoName + "/branches";
        return webClient.get()
                .uri(apiUrl)
                .retrieve()
                .bodyToFlux(BranchDto.class);
    }
}
