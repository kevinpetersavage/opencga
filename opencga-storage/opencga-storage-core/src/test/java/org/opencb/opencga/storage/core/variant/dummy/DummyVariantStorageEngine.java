/*
 * Copyright 2015-2017 OpenCB
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.opencb.opencga.storage.core.variant.dummy;

import org.opencb.biodata.models.variant.StudyEntry;
import org.opencb.biodata.models.variant.metadata.VariantMetadata;
import org.opencb.opencga.storage.core.config.StorageConfiguration;
import org.opencb.opencga.storage.core.exceptions.StorageEngineException;
import org.opencb.opencga.storage.core.metadata.StudyConfiguration;
import org.opencb.opencga.storage.core.metadata.StudyConfigurationManager;
import org.opencb.opencga.storage.core.variant.VariantStorageEngine;
import org.opencb.opencga.storage.core.variant.adaptors.VariantDBAdaptor;
import org.opencb.opencga.storage.core.variant.io.VariantImporter;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.List;

/**
 * Created on 28/11/16.
 *
 * TODO: Use Mockito
 *
 * @author Jacobo Coll &lt;jacobo167@gmail.com&gt;
 */
public class DummyVariantStorageEngine extends VariantStorageEngine {

    public DummyVariantStorageEngine() {
        super();
    }

    public DummyVariantStorageEngine(StorageConfiguration configuration) {
        super(configuration);
    }

    public static final String STORAGE_ENGINE_ID = "dummy";

    @Override
    public String getStorageEngineId() {
        return STORAGE_ENGINE_ID;
    }

    @Override
    public VariantDBAdaptor getDBAdaptor() throws StorageEngineException {
        return new DummyVariantDBAdaptor(dbName);
    }

    @Override
    public DummyVariantStoragePipeline newStoragePipeline(boolean connected) throws StorageEngineException {
        return new DummyVariantStoragePipeline(getConfiguration(), STORAGE_ENGINE_ID, getDBAdaptor(), getVariantReaderUtils());
    }

    @Override
    protected VariantImporter newVariantImporter() throws StorageEngineException {
        return new VariantImporter(getDBAdaptor()) {
            @Override
            public void importData(URI input, VariantMetadata metadata, List<StudyConfiguration> scs)
                    throws StorageEngineException, IOException {
            }
        };
    }

    @Override
    public void removeFiles(String study, List<String> files) throws StorageEngineException {
        List<Integer> fileIds = preRemoveFiles(study, files).getFileIds();
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        postRemoveFiles(study, fileIds, false);
    }

    @Override
    public void removeStudy(String studyName) throws StorageEngineException {
        getStudyConfigurationManager().lockAndUpdate(studyName, studyConfiguration -> {
            studyConfiguration.getIndexedFiles().clear();
            studyConfiguration.getCalculatedStats().clear();
            studyConfiguration.getInvalidStats().clear();
            studyConfiguration.getCohorts().put(studyConfiguration.getCohortIds().get(StudyEntry.DEFAULT_COHORT), Collections.emptySet());
            return studyConfiguration;
        });
    }

    @Override
    public StudyConfigurationManager getStudyConfigurationManager() throws StorageEngineException {
        return new StudyConfigurationManager(new DummyProjectMetadataAdaptor(), new DummyStudyConfigurationAdaptor(), new DummyVariantFileMetadataDBAdaptor());
    }
}
