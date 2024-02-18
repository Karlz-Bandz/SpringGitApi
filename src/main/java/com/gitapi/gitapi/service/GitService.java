package com.gitapi.gitapi.service;

import com.gitapi.gitapi.dto.BranchDto;
import com.gitapi.gitapi.dto.GitMasterDto;
import com.gitapi.gitapi.dto.RateLimitDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GitService {

    Mono<RateLimitDto> getLimit();

    Mono<GitMasterDto> getRepositories(String username);

    Flux<BranchDto> getBranchesForRepository(String userName, String repoName);
}
