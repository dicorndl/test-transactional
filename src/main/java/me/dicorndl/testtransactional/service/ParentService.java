package me.dicorndl.testtransactional.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.dicorndl.testtransactional.domain.Parent;
import me.dicorndl.testtransactional.domain.ParentRepository;

@Service
@RequiredArgsConstructor
public class ParentService {

    private final ParentRepository parentRepository;

    @Transactional
    public int countChildren(Integer parentId) {
        return parentRepository.findById(parentId)
            .map(Parent::countChildren)
            .orElseThrow(() -> new RuntimeException(String.format("No parent %d", parentId)));
    }

    @Transactional
    public int countChildrenWithFetch(Integer parentId) {
        return parentRepository.findByIdWithFetch(parentId)
            .map(Parent::countChildren)
            .orElseThrow(() -> new RuntimeException(String.format("No parent %d", parentId)));
    }
}
