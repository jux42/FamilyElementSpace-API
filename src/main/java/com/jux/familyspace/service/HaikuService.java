package com.jux.familyspace.service;

import com.jux.familyspace.api.FamilyElementServiceInterface;
import com.jux.familyspace.component.ElementAdder;
import com.jux.familyspace.component.HaikuSizeTracker;
import com.jux.familyspace.model.Haiku;
import com.jux.familyspace.repository.HaikuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HaikuService implements FamilyElementServiceInterface<Haiku> {

    private final HaikuRepository haikuRepository;
    private final HaikuSizeTracker sizeTracker;
    private final ElementAdder elementAdder;
    private Iterable<Haiku> haikus;


    @Override
    public Iterable<Haiku> getAllElements() {
        if (haikus == null || haikus.spliterator().getExactSizeIfKnown() != sizeTracker.getTotalSize()) {
            haikus = haikuRepository.findAll();
            return haikus;
        }
        return haikus;
    }

    @Override
    public Iterable<Haiku> getAllElementsByDate(Date date) {
        if (haikus == null || haikus.spliterator().getExactSizeIfKnown() != sizeTracker.getTotalSize()) {
            sizeTracker.setTotalSize(haikuRepository.findAll().size());
            haikus = haikuRepository.findAll();
        }
        List<Haiku> haikuList = new ArrayList<>();
        this.haikus.iterator().forEachRemaining(haiku -> {
            if (haiku.getDate().equals(date)) {
                haikuList.add(haiku);
            }
        });
        return haikus;
    }

    @Override
    public Haiku getElement(Long id) {
        return haikuRepository.findById(id).orElse(null);
    }

    @Override
    public String addElement(Haiku element) {
        try {
            String output = elementAdder.addElement(element);
            sizeTracker.incrementSize(1);
            return output;
        } catch (Exception e) {
            log.error(e.getMessage());
            return e.getMessage();
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
