package org.acme.infrastructure.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.acme.domain.models.question.*;
import org.acme.infrastructure.adapters.out.database.entities.question.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class QuestionMapper {

    @Inject
    ObjectMapper objectMapper;

    public Question toDomain(QuestionEntity entity) {
        if (entity instanceof ClosedQuestionEntity closed) {
            return mapClosedToDomain(closed);
        } 
        
        if (entity instanceof OpenQuestionEntity open) {
            return mapOpenToDomain(open);
        }

        throw new IllegalArgumentException("Unknown entity type");
    }

    public QuestionEntity toEntity(Question domain) {
        if (domain instanceof ClosedQuestion closed) {
            return mapClosedToEntity(closed);
        }
        
        if (domain instanceof OpenQuestion open) {
            return mapOpenToEntity(open);
        }
        
        throw new IllegalArgumentException("Unknown domain type");
    }

    private ClosedQuestion mapClosedToDomain(ClosedQuestionEntity entity) {
        ClosedQuestion q = new ClosedQuestion();
        fillCommonBaseFields(entity, q);
        
        q.setOptions(entity.options.stream().map(this::mapOptionToDomain).collect(Collectors.toList()));
        q.setCorrectOptionId(entity.correctOptionId);
        q.setCorrectExplanation(entity.correctExplanation);
        q.setWrongExplanations(parseJson(entity.wrongExplanations, new TypeReference<Map<String, String>>() {}));
        
        return q;
    }

    private OpenQuestion mapOpenToDomain(OpenQuestionEntity entity) {
        OpenQuestion q = new OpenQuestion();
        fillCommonBaseFields(entity, q);
      
        q.setModelAnswer(entity.modelAnswer);
        q.setReferenceConcepts(parseJson(entity.referenceConcepts, new TypeReference<List<String>>() {}));
        q.setRubric(parseJson(entity.rubric, new TypeReference<EvaluationRubric>() {}));
      
        return q;
    }

    private ClosedQuestionEntity mapClosedToEntity(ClosedQuestion domain) {
        ClosedQuestionEntity entity = new ClosedQuestionEntity();
      
        fillCommonEntityFields(domain, entity);
     
        entity.correctOptionId = domain.getCorrectOptionId();
        entity.correctExplanation = domain.getCorrectExplanation();
        entity.wrongExplanations = toJson(domain.getWrongExplanations());
        entity.options = domain.getOptions().stream().map(o -> mapOptionToEntity(o, entity)).collect(Collectors.toList());
        
        return entity;
    }

    private OpenQuestionEntity mapOpenToEntity(OpenQuestion domain) {
        OpenQuestionEntity entity = new OpenQuestionEntity();
        
        fillCommonEntityFields(domain, entity);
       
        entity.modelAnswer = domain.getModelAnswer();
        entity.referenceConcepts = toJson(domain.getReferenceConcepts());
        entity.rubric = toJson(domain.getRubric());
       
        return entity;
    }

    private void fillCommonBaseFields(QuestionEntity source, Question target) {
        target.setId(source.id);
        target.setStatement(source.statement);
        target.setMaxScore(source.maxScore);
        target.setType(source.type);
    }

    private void fillCommonEntityFields(Question source, QuestionEntity target) {
        target.id = source.getId();
        target.statement = source.getStatement();
        target.maxScore = source.getMaxScore();
    }

    private OptionQuestion mapOptionToDomain(OptionQuestionEntity entity) {
        return new OptionQuestion(entity.id, entity.text);
    }

    private OptionQuestionEntity mapOptionToEntity(OptionQuestion domain, QuestionEntity parent) {
        OptionQuestionEntity entity = new OptionQuestionEntity();
        
        entity.id = domain.getId();
        entity.text = domain.getText();
        entity.question = parent;
        
        return entity;
    }

    private String toJson(Object obj) {
        try { return objectMapper.writeValueAsString(obj); } 
        catch (JsonProcessingException e) { return null; }
    }

    private <T> T parseJson(String json, TypeReference<T> type) {
        try { return json == null ? null : objectMapper.readValue(json, type); } 
        catch (JsonProcessingException e) { return null; }
    }
}