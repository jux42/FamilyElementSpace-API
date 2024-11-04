package com.jux.familyspace.Service;

import com.jux.familyspace.Interface.FamilyElementServiceInterface;
import com.jux.familyspace.Model.FamilyElementType;
import com.jux.familyspace.Model.Haiku;
import com.jux.familyspace.Repository.HaikuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    public Haiku getElement(Long id) {
        return haikuRepository.findById(id).orElse(null);
    }

    @Override
    public String addElement(Haiku haiku) {
        try{
            assert haiku != null;
            assert haiku.getLine1() != null;
            assert haiku.getLine2() != null;
            assert haiku.getLine3() != null;
            haikuRepository.save(haiku);
            return "element saved";
        }catch (Exception e){
            log.error(e.getMessage());
            return ("error : " + e.getMessage());
        }

    }

    @Override
    public String removeElement(Long id) {

        try{
            assert haikuRepository.existsById(id);
            haikuRepository.deleteById(id);
            return "element removed";
        }catch (Exception e){
            log.error(e.getMessage());
            return ("error : " + e.getMessage());
        }
    }
}
