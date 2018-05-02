package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import dao.VacancyDao;
import model.Vacancy;
import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

public class VacancyControllerTest extends WithApplication {

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }

    /**
     * test: PUT - new object
     */
    @Test
    public void putNewVacancy() throws Exception {

        VacancyDao repository = app.injector().instanceOf(VacancyDao.class);
        Vacancy vacancy = new Vacancy(1L, "new name", null, null, null);
        JsonNode json = Json.toJson(vacancy);

        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(PUT)
                .bodyJson(json)
                .uri("/v1/vacancies");

        Result result = route(app, request);
        assertEquals(OK, result.status());
        assertEquals(vacancy, repository.findById(1L));
    }

    /**
     * test: PUT - existing object
     */
    @Test
    public void putExistingVacancy() throws Exception {

        VacancyDao repository = app.injector().instanceOf(VacancyDao.class);
        Vacancy existingVacancy = new Vacancy(2L, "new name", "321", "exp 1", "city 1");
        repository.save(existingVacancy);

        Vacancy newVacancy = new Vacancy(2L, "new name", "123", "exp 2", "city 2");
        JsonNode json = Json.toJson(newVacancy);

        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(PUT)
                .bodyJson(json)
                .uri("/v1/vacancies");

        Result result = route(app, request);
        assertEquals(OK, result.status());
        assertEquals(newVacancy, repository.findById(2L));
    }

    /**
     * test: GET by ID - not found
     */
    @Test
    public void getNotFound() throws Exception {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/v1/vacancies/1");

        Result result = route(app, request);
        assertEquals(NOT_FOUND, result.status());
    }

    /**
     * test: GET by ID - OK
     */
    @Test
    public void getByIdOk() throws Exception {

        VacancyDao repository = app.injector().instanceOf(VacancyDao.class);
        Vacancy existingVacancy = new Vacancy(3L, "new name", "321", "exp 1", "city 1");
        repository.save(existingVacancy);

        JsonNode expectedJson = Json.toJson(existingVacancy);

        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/v1/vacancies/3");

        Result result = route(app, request);
        assertEquals(OK, result.status());

        final String body = contentAsString(result);
        assertEquals(expectedJson.toString(), body); // not really the correct way - need to compare jsons, not strings
    }

    // TODO: other things to test:

    // test: PUT - existing object
    // test: PUT - empty ID error
    // test: PUT - empty NAME error
    // test: PUT - negative salary
    // test: PUT - wrong field length
    // test: DELETE - OK
    // test: DELETE - object doesn't exist
    // test: GET all - empty list
    // test: GET all - multiple elements
    // test: POST - not supported



}
