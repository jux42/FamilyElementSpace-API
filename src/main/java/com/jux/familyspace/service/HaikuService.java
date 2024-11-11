package com.jux.familyspace.service;

import com.jux.familyspace.api.FamilyElementServiceInterface;
import com.jux.familyspace.model.Haiku;
import com.jux.familyspace.repository.HaikuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class HaikuService implements FamilyElementServiceInterface<Haiku> {

    private final HaikuRepository haikuRepository;

    @Override
    public Iterable<Haiku> getAllElements() {
        return haikuRepository.findAll();
    }

    @Override
    public Iterable<Haiku> getAllElementsByDate(Date date) {
        return haikuRepository.getHaikusByDate(date);
    }

    @Override
    public Haiku getElement(Long id) {
        return haikuRepository.findById(id).orElse(null);
    }

    @Override
    public String addElement(Haiku haiku) {
        try {
            assert haiku != null;
            assert haiku.getLine1() != null;
            assert haiku.getLine2() != null;
            assert haiku.getLine3() != null;
            haikuRepository.save(haiku);
            return "element saved";
        } catch (Exception e) {
            log.error(e.getMessage());
            return ("error : " + e.getMessage());
        }

    }

    @Override
    public String removeElement(Long id) {

        try {
            assert haikuRepository.existsById(id);
            haikuRepository.deleteById(id);
            return "element removed";
        } catch (Exception e) {
            log.error(e.getMessage());
            return ("error : " + e.getMessage());
        }
    }
}
