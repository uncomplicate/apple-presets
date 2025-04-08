/*
 * Copyright (C) 2025 Dragan Djuric
 *
 * Licensed under the Apache License, Version 2.0
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * or as provided in the LICENSE.txt file that accompanied this code.
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uncomplicate.javacpp.accelerate.presets;

import java.util.List;
import org.bytedeco.javacpp.ClassProperties;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.LoadEnabled;
import org.bytedeco.javacpp.annotation.NoException;
import org.bytedeco.javacpp.annotation.Platform;
import org.bytedeco.javacpp.annotation.Properties;
import org.bytedeco.javacpp.presets.javacpp;
import org.bytedeco.javacpp.tools.Info;
import org.bytedeco.javacpp.tools.InfoMap;
import org.bytedeco.javacpp.tools.InfoMapper;

/**
 *
 * @author Dragan Djuric
 */
@Properties(inherit = blas_new.class, global = "uncomplicate.javacpp.accelerate.global.sparse", value = {
        @Platform(value = "macosx-arm64",
                  include = {"Types.h", "BLAS.h", "Sparse.h", "Solve.h"},
                  includepath = {"/Library/Developer/CommandLineTools/SDKs/MacOSX.sdk/System/Library/Frameworks/vecLib.framework/Headers/Sparse/",

                      "/Library/Developer/CommandLineTools/SDKs/MacOSX.sdk/usr/include/"})})
@NoException
public class sparse implements LoadEnabled, InfoMapper {

    static { Loader.checkVersion("uncomplicate", "accelerate"); }

    @Override public void init(ClassProperties properties) {
        String platform = properties.getProperty("platform");
        List<String> includePaths = properties.get("platform.includepath");
        List<String> includes = properties.get("platform.include");
    }

    @Override public void map(InfoMap infoMap) {
        infoMap.put(new Info("__restrict").cppTypes().annotations())
            .put(new Info("sparse_m_float").pointerTypes("sparse_matrix_float"))
            .put(new Info("sparse_m_double").pointerTypes("sparse_matrix_double"))
            .put(new Info("__has_attribute(enum_extensibility) && !defined(__swift__)").define(true))
            .put(new Info("__has_feature(objc_fixed_enum) || __has_extension(cxx_strong_enums)").define(false))
            .put(new Info("SparseStatusReleased").skip());
    }

}
