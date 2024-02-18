package com.gitapi.gitapi.controller.impl;

import com.gitapi.gitapi.controller.GitController;
import com.gitapi.gitapi.dto.BranchDto;
import com.gitapi.gitapi.dto.GitMasterDto;
import com.gitapi.gitapi.dto.RateLimitDto;
import com.gitapi.gitapi.service.GitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class GitControllerImpl implements GitController {

    private final GitService gitService;

    public GitControllerImpl(GitService gitService) {
        this.gitService = gitService;
    }

    @Override
    public Mono<ResponseEntity<RateLimitDto>> getLimit() {
        return gitService.getLimit()
                .map(value -> ResponseEntity.ok().body(value))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Override
    public Mono<ResponseEntity<GitMasterDto>> getRepositories(String username) {
        return gitService.getRepositories(username)
                .map(value -> ResponseEntity.ok().body(value))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Override
    public Flux<ResponseEntity<BranchDto>> getBranches(String username, String repoName) {
        return gitService.getBranchesForRepository(username, repoName)
                .map(value -> ResponseEntity.ok().body(value))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
