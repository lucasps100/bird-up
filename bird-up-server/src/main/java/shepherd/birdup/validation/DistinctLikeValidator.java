package shepherd.birdup.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import shepherd.birdup.data.LikeJdbcTemplateRepository;
import shepherd.birdup.data.LikeRepository;
import shepherd.birdup.models.Like;
import shepherd.birdup.models.Location;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class DistinctLikeValidator implements ConstraintValidator<DistinctLike, Like> {

    @Autowired
    private LikeRepository repository;

    @Override
    public void initialize(DistinctLike constraintAnnotation) {}

    @Override
    public boolean isValid(Like like, ConstraintValidatorContext constraintValidatorContext) {
        if(like == null) {
            return false;
        }
        List<Like> existing = repository.findByPostId(like.getPostId());
        return existing.stream()
                .noneMatch(l -> l.getLikerAccount().getAppUserId() == like.getLikerAccount().getAppUserId());
    }
}
