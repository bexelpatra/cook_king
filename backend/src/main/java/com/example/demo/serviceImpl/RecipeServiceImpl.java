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
import jdk.nashorn.internal.objects.annotations.Property;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@PropertySource(value = "classpath:application.properties")
public class RecipeServiceImpl implements RecipeService {
    @Value("${file.absolutepath}")
    private final String absolutePath="D:/class/cook_king/frontend/public/imgs/";
    @Value("${file.localpath}")
    private final String localPath = "D:/class/cook_king/frontend/public/imgs/";
    @Value("${file.url}")
    private final String urlPath = "imgs/";
    private final RecipeRepository recipeRepository;
    private final ContentRepository contentRepository;
    private int maxInt = Integer.MAX_VALUE;

    @Override
    public List<RecipesEntity> getRecipeByFirstCategory(FirstCategoryKind firstCategoryKind, int page) {
        if(firstCategoryKind==null) return new ArrayList<RecipesEntity>();
        if(page <= 0) page = maxInt;

        return recipeRepository.findRecipesEntitiesByIdIsLessThanAndFirstCategoryKindOrderByIdDesc(
                page,
                firstCategoryKind,
                PageRequest.of(0,20, Sort.by(Sort.Direction.DESC,"id")));
    }

    @Override
    public List<RecipesEntity> getRecipeByFirstCategoryOrderByFavoriteCount(FirstCategoryKind firstCategoryKind, int page) {
        if(firstCategoryKind==null) return new ArrayList<RecipesEntity>();
        if(page <= 0) page = maxInt;
        return recipeRepository.findPopular20Recipes(firstCategoryKind.getValue(),page);
    }

    @Override
    public List<RecipesEntity> getRecipeByCategories(FirstCategoryKind[] firstCategoryKind, SecondCategoryKind[] secondCategoryKind, int page) {
        if(page <= 0) page = maxInt;
        return recipeRepository.findRecipesEntitiesByIdIsLessThanAndFirstCategoryKindInAndSecondCategoryKindInOrderByIdDesc(page,firstCategoryKind,secondCategoryKind,PageRequest.of(0,20));
    }

    @Override
    public List<RecipesEntity> getRecipeByCategoriesAndKeyword(FirstCategoryKind[] firstCategoryKind, SecondCategoryKind[] secondCategoryKind,String keyword, int page) {
        if(page <= 0) page = maxInt;
        List<RecipesEntity> recipesEntities = new ArrayList<>();
        if(keyword == null|| keyword.equals("")) {
            recipesEntities = recipeRepository.findRecipesEntitiesByIdIsLessThanAndFirstCategoryKindInAndSecondCategoryKindInOrderByIdDesc(page,firstCategoryKind,secondCategoryKind, PageRequest.of(0,20));
        } else{
            recipesEntities =recipeRepository.findRecipesEntitiesByIdIsLessThanAndFirstCategoryKindInAndSecondCategoryKindInAndTitleContainingOrderByIdDesc(page,firstCategoryKind,secondCategoryKind,keyword,PageRequest.of(0,20));
        }
        return recipesEntities;
    }
    @Override
    public RecipesEntity save(RecipesDto recipesDto) {
        return recipeRepository.save(Utils.to(recipesDto));
    }

    @Override
    public RecipesEntity saveRecipeAndImage(RecipesDto recipesDto, MultiFileDto multiFileDto, UsersEntity usersEntity) throws Exception {

        RecipesEntity recipesEntity = recipesDto.convert().to();
        if(recipesEntity.getFirstCategoryKind()==null || recipesEntity.getSecondCategoryKind()==null) throw new RuntimeException("category not found");
        recipesEntity.setUsersEntity(usersEntity);

        recipesEntity = recipeRepository.save(recipesEntity);
        saveContentEntity(multiFileDto,recipesEntity,urlPath+usersEntity.getId()+"/"+recipesEntity.getId()+"/");

        // 이미지 저장
        Utils.saveImage(multiFileDto.getFile(),usersEntity,recipesEntity.getId());
        return recipesEntity;
    }

    // recipe 수정할때 사용한다. recipeEntity를 받아서 content를 삭제하고 나머지 내용들을 싹 업어친다.
    @Override
    @Transactional
    public RecipesEntity deleteAndSaveRecipeAndImage(RecipesEntity recipesEntity, RecipesDto recipesDto, MultiFileDto multiFileDto, UsersEntity usersEntity) throws Exception {

        // #1 dto를 통해 새롭게 받아온 정보를 entity에 업데이트
        recipesDto.update(recipesEntity);
        recipesEntity.getContentEntities().clear();
        contentRepository.deleteContents(recipesDto.getId());
        recipesEntity.setContentEntities(
                saveContentEntity(multiFileDto,recipesEntity,urlPath+usersEntity.getId()+"/"+recipesEntity.getId()+"/")
        );
        // 이미지 저장
        Utils.deleteAndSaveImage(multiFileDto.getFile(),usersEntity,recipesEntity.getId());
        recipeRepository.save(recipesEntity);
        return recipesEntity;
    }
    @Override
    public RecipesDto getRecipeById(int recipeId) {
        Optional<RecipesEntity> optional = recipeRepository.getRecipesEntityById(recipeId);
        if(!optional.isPresent()) return null;
        RecipesEntity recipesEntity = optional.get();
        RecipesDto recipesDto = recipesEntity.toWithContents();

        return recipesDto;
    }

    @Override
    public Optional<RecipesEntity> getRecipeEntityById(int recipeId) {
        return recipeRepository.getRecipesEntityById(recipeId);
    }
    private List<ContentEntity> saveContentEntity(MultiFileDto multiFileDto, RecipesEntity recipesEntity, String localPath){
        int count = 1;
        Integer[] kinds = multiFileDto.getKind();
        Integer[] orders = multiFileDto.getOrder();
        String[] texts = multiFileDto.getText();
        List<ContentEntity> contentEntities= new ArrayList<>();
        for(int i=0;i< orders.length;i++){
            String fileName = count+".png";

            contentEntities.add(ContentEntity.builder()
                    .contentKind(ContentKind.of(kinds[i]))
                    .description(texts[i])
                    .order(count++)
                    .path(localPath)
                    .absolutePath(absolutePath+recipesEntity.getUsersEntity().getId()+"/"+recipesEntity.getId()+"/")
                    .name(fileName)
                    .url(localPath+fileName)
                    .recipesEntity(recipesEntity)
                    .title("이미지 파일")
                    .build());
        }
        count=0;
        return contentRepository.saveAll(contentEntities);
    }

    private void deleteContent(RecipesEntity recipesEntity){
        recipesEntity.setContentEntities(null);
        recipeRepository.save(recipesEntity);
    }

    @Override
    @Deprecated
    public boolean deleteContent(ContentEntity contentEntity) {
        contentRepository.delete(contentEntity);
        File file = new File(localPath + contentEntity.getName());
        if(file.exists()){
            return file.delete();
        }
        return false;
    }

    @Override
    @Deprecated
    public boolean deleteContent(List<ContentEntity> contentEntity) {
        int nullFile = contentEntity.stream().filter(entity ->!new File(entity.getUrl()).exists()).toArray().length;
        if(nullFile>0) return false;
        for (ContentEntity entity : contentEntity) {
            deleteContent(entity);
        }
        return false;
    }

    @Override
    public String test(String path) {
        File file = new File(path);

        return file.getPath() + ":" + file.getAbsolutePath();
    }
}
