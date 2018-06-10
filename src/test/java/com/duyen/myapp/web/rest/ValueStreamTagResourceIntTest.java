package com.duyen.myapp.web.rest;

import com.duyen.myapp.MainApp;

import com.duyen.myapp.domain.ValueStreamTag;
import com.duyen.myapp.repository.ValueStreamTagRepository;
import com.duyen.myapp.service.ValueStreamTagService;
import com.duyen.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.duyen.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ValueStreamTagResource REST controller.
 *
 * @see ValueStreamTagResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApp.class)
public class ValueStreamTagResourceIntTest {

    private static final String DEFAULT_TAG_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TAG_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_X = 1;
    private static final Integer UPDATED_X = 2;

    private static final Integer DEFAULT_Y = 1;
    private static final Integer UPDATED_Y = 2;

    private static final Integer DEFAULT_HEIGHT = 1;
    private static final Integer UPDATED_HEIGHT = 2;

    private static final Integer DEFAULT_WIDTH = 1;
    private static final Integer UPDATED_WIDTH = 2;

    @Autowired
    private ValueStreamTagRepository valueStreamTagRepository;

    @Autowired
    private ValueStreamTagService valueStreamTagService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restValueStreamTagMockMvc;

    private ValueStreamTag valueStreamTag;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ValueStreamTagResource valueStreamTagResource = new ValueStreamTagResource(valueStreamTagService);
        this.restValueStreamTagMockMvc = MockMvcBuilders.standaloneSetup(valueStreamTagResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ValueStreamTag createEntity(EntityManager em) {
        ValueStreamTag valueStreamTag = new ValueStreamTag()
            .tagName(DEFAULT_TAG_NAME)
            .x(DEFAULT_X)
            .y(DEFAULT_Y)
            .height(DEFAULT_HEIGHT)
            .width(DEFAULT_WIDTH);
        return valueStreamTag;
    }

    @Before
    public void initTest() {
        valueStreamTag = createEntity(em);
    }

    @Test
    @Transactional
    public void createValueStreamTag() throws Exception {
        int databaseSizeBeforeCreate = valueStreamTagRepository.findAll().size();

        // Create the ValueStreamTag
        restValueStreamTagMockMvc.perform(post("/api/value-stream-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(valueStreamTag)))
            .andExpect(status().isCreated());

        // Validate the ValueStreamTag in the database
        List<ValueStreamTag> valueStreamTagList = valueStreamTagRepository.findAll();
        assertThat(valueStreamTagList).hasSize(databaseSizeBeforeCreate + 1);
        ValueStreamTag testValueStreamTag = valueStreamTagList.get(valueStreamTagList.size() - 1);
        assertThat(testValueStreamTag.getTagName()).isEqualTo(DEFAULT_TAG_NAME);
        assertThat(testValueStreamTag.getX()).isEqualTo(DEFAULT_X);
        assertThat(testValueStreamTag.getY()).isEqualTo(DEFAULT_Y);
        assertThat(testValueStreamTag.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testValueStreamTag.getWidth()).isEqualTo(DEFAULT_WIDTH);
    }

    @Test
    @Transactional
    public void createValueStreamTagWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = valueStreamTagRepository.findAll().size();

        // Create the ValueStreamTag with an existing ID
        valueStreamTag.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restValueStreamTagMockMvc.perform(post("/api/value-stream-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(valueStreamTag)))
            .andExpect(status().isBadRequest());

        // Validate the ValueStreamTag in the database
        List<ValueStreamTag> valueStreamTagList = valueStreamTagRepository.findAll();
        assertThat(valueStreamTagList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllValueStreamTags() throws Exception {
        // Initialize the database
        valueStreamTagRepository.saveAndFlush(valueStreamTag);

        // Get all the valueStreamTagList
        restValueStreamTagMockMvc.perform(get("/api/value-stream-tags?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(valueStreamTag.getId().intValue())))
            .andExpect(jsonPath("$.[*].tagName").value(hasItem(DEFAULT_TAG_NAME.toString())))
            .andExpect(jsonPath("$.[*].x").value(hasItem(DEFAULT_X)))
            .andExpect(jsonPath("$.[*].y").value(hasItem(DEFAULT_Y)))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT)))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH)));
    }

    @Test
    @Transactional
    public void getValueStreamTag() throws Exception {
        // Initialize the database
        valueStreamTagRepository.saveAndFlush(valueStreamTag);

        // Get the valueStreamTag
        restValueStreamTagMockMvc.perform(get("/api/value-stream-tags/{id}", valueStreamTag.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(valueStreamTag.getId().intValue()))
            .andExpect(jsonPath("$.tagName").value(DEFAULT_TAG_NAME.toString()))
            .andExpect(jsonPath("$.x").value(DEFAULT_X))
            .andExpect(jsonPath("$.y").value(DEFAULT_Y))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT))
            .andExpect(jsonPath("$.width").value(DEFAULT_WIDTH));
    }

    @Test
    @Transactional
    public void getNonExistingValueStreamTag() throws Exception {
        // Get the valueStreamTag
        restValueStreamTagMockMvc.perform(get("/api/value-stream-tags/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateValueStreamTag() throws Exception {
        // Initialize the database
        valueStreamTagService.save(valueStreamTag);

        int databaseSizeBeforeUpdate = valueStreamTagRepository.findAll().size();

        // Update the valueStreamTag
        ValueStreamTag updatedValueStreamTag = valueStreamTagRepository.findOne(valueStreamTag.getId());
        // Disconnect from session so that the updates on updatedValueStreamTag are not directly saved in db
        em.detach(updatedValueStreamTag);
        updatedValueStreamTag
            .tagName(UPDATED_TAG_NAME)
            .x(UPDATED_X)
            .y(UPDATED_Y)
            .height(UPDATED_HEIGHT)
            .width(UPDATED_WIDTH);

        restValueStreamTagMockMvc.perform(put("/api/value-stream-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedValueStreamTag)))
            .andExpect(status().isOk());

        // Validate the ValueStreamTag in the database
        List<ValueStreamTag> valueStreamTagList = valueStreamTagRepository.findAll();
        assertThat(valueStreamTagList).hasSize(databaseSizeBeforeUpdate);
        ValueStreamTag testValueStreamTag = valueStreamTagList.get(valueStreamTagList.size() - 1);
        assertThat(testValueStreamTag.getTagName()).isEqualTo(UPDATED_TAG_NAME);
        assertThat(testValueStreamTag.getX()).isEqualTo(UPDATED_X);
        assertThat(testValueStreamTag.getY()).isEqualTo(UPDATED_Y);
        assertThat(testValueStreamTag.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testValueStreamTag.getWidth()).isEqualTo(UPDATED_WIDTH);
    }

    @Test
    @Transactional
    public void updateNonExistingValueStreamTag() throws Exception {
        int databaseSizeBeforeUpdate = valueStreamTagRepository.findAll().size();

        // Create the ValueStreamTag

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restValueStreamTagMockMvc.perform(put("/api/value-stream-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(valueStreamTag)))
            .andExpect(status().isCreated());

        // Validate the ValueStreamTag in the database
        List<ValueStreamTag> valueStreamTagList = valueStreamTagRepository.findAll();
        assertThat(valueStreamTagList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteValueStreamTag() throws Exception {
        // Initialize the database
        valueStreamTagService.save(valueStreamTag);

        int databaseSizeBeforeDelete = valueStreamTagRepository.findAll().size();

        // Get the valueStreamTag
        restValueStreamTagMockMvc.perform(delete("/api/value-stream-tags/{id}", valueStreamTag.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ValueStreamTag> valueStreamTagList = valueStreamTagRepository.findAll();
        assertThat(valueStreamTagList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ValueStreamTag.class);
        ValueStreamTag valueStreamTag1 = new ValueStreamTag();
        valueStreamTag1.setId(1L);
        ValueStreamTag valueStreamTag2 = new ValueStreamTag();
        valueStreamTag2.setId(valueStreamTag1.getId());
        assertThat(valueStreamTag1).isEqualTo(valueStreamTag2);
        valueStreamTag2.setId(2L);
        assertThat(valueStreamTag1).isNotEqualTo(valueStreamTag2);
        valueStreamTag1.setId(null);
        assertThat(valueStreamTag1).isNotEqualTo(valueStreamTag2);
    }
}
