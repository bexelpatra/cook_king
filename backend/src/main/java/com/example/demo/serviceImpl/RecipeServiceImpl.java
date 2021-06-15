package com.example.demo.serviceImpl;

import com.example.demo.dto.ContentDto;
import com.example.demo.dto.MultiFileDto;
import com.example.demo.dto.RecipesDto;
import com.example.demo.entity.ContentEntity;
import com.example.demo.entity.RecipesEntity;
import com.example.demo.entity.UsersEntity;
import com.example.demo.enums.ContentKind;
import com.example.demo.enums.FirstCategoryKind;
import com.example.demo.enums.SecondCategoryKind;
import com.example.demo.repository.ContentRepository;
import com.example.demo.repository.RecipeRepository;
import com.example.demo.service.RecipeService;
import com.example.demo.utilities.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final ContentRepository contentRepository;
    private int maxInt = Integer.MAX_VALUE;

    private final String localPath = "D:/coook/";
    @Override
    public List<RecipesEntity> getRecipeByFirstCategory(FirstCategoryKind firstCategoryKind, int page) {
        if(firstCategoryKind==null) return new ArrayList<RecipesEntity>();
        if(page <= 0) page = maxInt;

        return recipeRepository.findRecipesEntitiesByIdIsLessThanAndFirstCategoryEntityKindOrderByIdDesc(
                page,
                firstCategoryKind,
                PageRequest.of(0,20, Sort.by(Sort.Direction.DESC,"id")));
    }

    @Override
    public List<RecipesEntity> getRecipeByCategories(FirstCategoryKind[] firstCategoryKind, SecondCategoryKind[] secondCategoryKind, int page) {
        if(page <= 0) page = maxInt;
        return recipeRepository.findRecipesEntitiesByIdIsLessThanAndFirstCategoryEntityInAndSecondCategoryEntityInOrderByIdDesc(page,firstCategoryKind,secondCategoryKind,PageRequest.of(0,20));
    }

    @Override
    public RecipesEntity save(RecipesDto recipesDto) {
        return recipeRepository.save(Utils.to(recipesDto));
    }

    @Override
    public RecipesEntity saveRecipeAndImage(RecipesDto recipesDto, MultiFileDto multiFileDto, UsersEntity usersEntity) throws Exception {

        RecipesEntity recipesEntity = recipesDto.convert().to();
        recipesEntity.setUsersEntity(usersEntity);

        recipesEntity = recipeRepository.save(recipesEntity);
        saveContentEntity(multiFileDto,recipesEntity,localPath);
        // 이미지 저장
        Utils.saveImage(multiFileDto.getFile(),usersEntity,recipesEntity.getId());
        return recipesEntity;
    }

    @Override
    public RecipesDto getRecipeById(int recipeId) {
        Optional<RecipesEntity> optional = recipeRepository.getRecipesEntityById(recipeId);
        if(!optional.isPresent()) return null;
        RecipesEntity recipesEntity = optional.get();
        RecipesDto recipesDto = recipesEntity.to();

        recipesDto.setContentDtos(Utils.to(ContentDto.class,recipesEntity.getContentEntities()));

        return recipesDto;
    }

    private List<ContentEntity> saveContentEntity(MultiFileDto multiFileDto, RecipesEntity recipesEntity, String localPath){
        Integer[] kinds = multiFileDto.getKind();
        Integer[] orders = multiFileDto.getOrder();
        String[] texts = multiFileDto.getText();
        List<ContentEntity> contentEntities= new ArrayList<>();
        for(int i=0;i< orders.length;i++){
            String fileName = orders[i]+".png";

            contentEntities.add(ContentEntity.builder()
                    .contentKind(ContentKind.byValue(kinds[i]))
                    .description(texts[i])
                    .order(orders[i])
                    .path(localPath)
                    .name(fileName)
                    .url(localPath+fileName)
                    .recipesEntity(recipesEntity)
                    .title("이미지 파일")
                    .build());
        }

        return contentRepository.saveAll(contentEntities);
    }

    @Override
    public boolean deleteContent(ContentEntity contentEntity) {
        contentRepository.delete(contentEntity);
        File file = new File(localPath + contentEntity.getName());
        if(file.exists()){
            return file.delete();
        }
        return false;
    }

    @Override
    public boolean deleteContent(List<ContentEntity> contentEntity) {
        int nullFile = contentEntity.stream().filter(entity ->!new File(entity.getUrl()).exists()).toArray().length;
        if(nullFile>0) return false;
        for (ContentEntity entity : contentEntity) {
            deleteContent(entity);
        }
        return false;
    }
}
