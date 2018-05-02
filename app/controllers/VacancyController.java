package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import dao.VacancyDao;
import model.Vacancy;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;

public class VacancyController extends Controller {

    private static final Logger.ALogger logger = Logger.of(VacancyController.class);

    private final VacancyDao vacancyDao;
    private final FormFactory formFactory;

    @Inject
    public VacancyController(VacancyDao vacancyDao, FormFactory formFactory) {
        this.vacancyDao = vacancyDao;
        this.formFactory = formFactory;
    }

    public Result list() {
        return ok(Json.toJson(vacancyDao.findAll()));
    }

    @BodyParser.Of(value = BodyParser.Json.class)
    public Result create() {
        JsonNode json = request().body().asJson();

        Form<Vacancy> vacancyBindingResult = formFactory.form(Vacancy.class).bind(json);
        if (vacancyBindingResult.hasErrors()) {
            logger.error("PUT vacancy data errors! {}", vacancyBindingResult.errorsAsJson());
            return badRequest(vacancyBindingResult.errorsAsJson());
        }

        final Vacancy vacancy = vacancyBindingResult.get();

        logger.debug("Going to create/update vacancy: {}", vacancy);

        Vacancy existingVacancy = vacancyDao.findById(vacancy.getId());

        logger.debug("existingVacancy: {}", existingVacancy);

        if (existingVacancy != null) {
            vacancyDao.update(vacancy);
        } else {
            vacancyDao.save(vacancy);
        }

        return ok();
    }

    public Result show(Long id) {

        Vacancy vacancy = vacancyDao.findById(id);

        return (vacancy == null) ? notFound() : ok(Json.toJson(vacancy));
    }

    public Result delete(Long id) {
        try {
            Vacancy vacancy = vacancyDao.findById(id);

            if (vacancy == null) {
                return notFound();
            }

            vacancyDao.deleteById(id);
            return ok();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return internalServerError(e.getMessage());
        }
    }


}
