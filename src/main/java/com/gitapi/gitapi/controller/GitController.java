package com.gitapi.gitapi.controller;

import com.gitapi.gitapi.dto.BranchDto;
import com.gitapi.gitapi.dto.GitMasterDto;
import com.gitapi.gitapi.dto.RateLimitDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/git")
public interface GitController {

    @GetMapping("/limit")
    Mono<ResponseEntity<RateLimitDto>> getLimit();

    @GetMapping("/repos/{username}")
    Mono<ResponseEntity<GitMasterDto>> getRepositories(@PathVariable("username") String username);

    @GetMapping("/branch/{username}/{repoName}")
    Flux<ResponseEntity<BranchDto>> getBranches(
            @PathVariable("username") String username,
            @PathVariable("repoName") String repoName);

}
