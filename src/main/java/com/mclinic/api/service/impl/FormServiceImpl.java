/**
 * Copyright 2012 Muzima Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mclinic.api.service.impl;

import com.google.inject.Inject;
import com.mclinic.api.dao.FormDao;
import com.mclinic.api.dao.FormDataDao;
import com.mclinic.api.dao.FormTemplateDao;
import com.mclinic.api.model.Form;
import com.mclinic.api.model.FormData;
import com.mclinic.api.model.FormTemplate;
import com.mclinic.api.service.FormService;
import com.mclinic.search.api.util.StringUtil;
import com.mclinic.util.Constants;
import org.apache.lucene.queryParser.ParseException;

import java.io.IOException;
import java.util.List;

public class FormServiceImpl implements FormService {

    @Inject
    private FormDao formDao;

    @Inject
    private FormDataDao formDataDao;

    @Inject
    private FormTemplateDao formTemplateDao;

    protected FormServiceImpl() {
    }

    /**
     * Download a single form record from the form rest resource into the local lucene repository.
     *
     * @param uuid the uuid of the form.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws IOException    when search api unable to process the resource.
     * @should download form with matching uuid.
     */
    @Override
    public Form downloadFormByUuid(final String uuid) throws IOException, ParseException {
        formDao.download(uuid, Constants.UUID_FORM_RESOURCE);
        return getFormByUuid(uuid);
    }

    /**
     * Download all forms with name similar to the partial name passed in the parameter.
     *
     * @param name the partial name of the form to be downloaded. When empty, will return all forms available.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws IOException    when search api unable to process the resource.
     * @should download all form with partially matched name.
     * @should download all form when name is empty.
     */
    @Override
    public List<Form> downloadFormsByName(final String name) throws IOException, ParseException {
        formDao.download(name, Constants.SEARCH_FORM_RESOURCE);
        return getFormByName(name);
    }

    /**
     * Get form by the uuid of the form.
     *
     * @param uuid the form uuid.
     * @return form with matching uuid or null when no form match the uuid.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws IOException    when search api unable to process the resource.
     * @should return form with matching uuid.
     * @should return null when no form match the uuid.
     */
    @Override
    public Form getFormByUuid(final String uuid) throws IOException, ParseException {
        return formDao.getByUuid(uuid);
    }

    /**
     * Get all form with matching name (or partial name).
     *
     * @param name the form name.
     * @return form with matching uuid or null when no form match the uuid.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws IOException    when search api unable to process the resource.
     * @should return form with matching uuid.
     * @should return null when no form match the uuid.
     */
    @Override
    public List<Form> getFormByName(final String name) throws IOException, ParseException {
        return formDao.getByName(name);
    }

    /**
     * @return all registered forms or empty list when no form is registered.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws IOException    when search api unable to process the resource.
     * @should return all registered forms.
     * @should return empty list when no form is registered.
     */
    @Override
    public List<Form> getAllForms() throws IOException, ParseException {
        return formDao.getAll();
    }

    /**
     * Delete form from the repository.
     *
     * @param form the form to be deleted.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws IOException    when search api unable to process the resource.
     */
    @Override
    public void deleteForm(final Form form) throws IOException, ParseException {
        try {
            formDao.delete(form, Constants.SEARCH_FORM_RESOURCE);
        } finally {
            formDao.delete(form, Constants.UUID_COHORT_RESOURCE);
        }
    }

    /**
     * Save a new form template to the repository.
     *
     * @param formTemplate the form template to be saved.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws IOException    when search api unable to process the resource.
     */
    @Override
    public void saveFormTemplate(final FormTemplate formTemplate) throws IOException, ParseException {
        formTemplateDao.save(formTemplate, Constants.LOCAL_FORM_TEMPLATE_RESOURCE);
    }

    /**
     * Get a form template by the uuid.
     *
     * @param uuid the form template uuid.
     * @return the form template.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws IOException    when search api unable to process the resource.
     */
    @Override
    public FormTemplate getFormTemplateByUuid(final String uuid) throws IOException, ParseException {
        return formTemplateDao.getByUuid(uuid);
    }

    /**
     * Get all saved form templates from the local repository.
     *
     * @return all saved form templates or empty list when there's no form template saved.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws IOException    when search api unable to process the resource.
     */
    @Override
    public List<FormTemplate> getAllFormTemplates() throws IOException, ParseException {
        return formTemplateDao.getAll();
    }

    /**
     * Delete a form template from the repository.
     *
     * @param formTemplate the form template to be deleted.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws IOException    when search api unable to process the resource.
     */
    @Override
    public void deleteFormTemplate(final FormTemplate formTemplate) throws IOException, ParseException {
        formTemplateDao.delete(formTemplate, Constants.LOCAL_FORM_TEMPLATE_RESOURCE);
    }

    /**
     * Save a new form data object to the database.
     *
     * @param formData the form data to be saved.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws IOException    when search api unable to process the resource.
     */
    @Override
    public void saveFormData(final FormData formData) throws IOException, ParseException {
        formDataDao.save(formData, Constants.LOCAL_FORM_DATA_RESOURCE);
    }

    /**
     * Get a single form data object from the local data repository.
     *
     * @param uuid the uuid for the form data.
     * @return the form data object.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws IOException    when search api unable to process the resource.
     */
    @Override
    public FormData getFormDataByUuid(final String uuid) throws IOException, ParseException {
        return formDataDao.getByUuid(uuid);
    }

    /**
     * Get all form data filtering on the status of the form data.
     *
     * @param status the status of the form data (optional).
     * @return all form data with matching status.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws IOException    when search api unable to process the resource.
     */
    @Override
    public List<FormData> getAllFormData(final String status) throws IOException, ParseException {
        return formDataDao.getAll(StringUtil.EMPTY, StringUtil.EMPTY, status);
    }

    /**
     * Get form data associated with certain user with filtering on the status of the form data.
     *
     * @param userUuid the uuid of the user.
     * @param status   the status of the form data (optional).
     * @return all form data for the user with matching status.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws IOException    when search api unable to process the resource.
     */
    @Override
    public List<FormData> getFormDataByUser(final String userUuid, final String status) throws IOException, ParseException {
        return formDataDao.getAll(StringUtil.EMPTY, userUuid, status);
    }

    /**
     * Get form data associated with certain user with filtering on the status of the form data.
     *
     * @param patientUuid the uuid of the patient
     * @param status      the status of the form data (optional).
     * @return all form data for the patient with matching status.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws IOException    when search api unable to process the resource.
     */
    @Override
    public List<FormData> getFormDataByPatient(final String patientUuid, final String status) throws IOException, ParseException {
        return formDataDao.getAll(patientUuid, StringUtil.EMPTY, status);
    }

    /**
     * Delete an instance of form data.
     *
     * @param formData the form data
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws IOException    when search api unable to process the resource.
     */
    @Override
    public void deleteFormDate(final FormData formData) throws IOException, ParseException {
        formDataDao.delete(formData, Constants.LOCAL_FORM_DATA_RESOURCE);
    }
}
