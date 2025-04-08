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
@Properties(inherit = javacpp.class, global = "uncomplicate.javacpp.accelerate.global.vdsp", value = {
        @Platform(value = "macosx-arm64",
                  include = {"vDSP.h"},
                  includepath = {"/Library/Developer/CommandLineTools/SDKs/MacOSX.sdk/System/Library/Frameworks/vecLib.framework/Headers",
                      "/Library/Developer/CommandLineTools/SDKs/MacOSX.sdk/System/Library/Frameworks/CoreFoundation.framework/Headers/",
                      "/Library/Developer/CommandLineTools/SDKs/MacOSX.sdk/usr/include/"})})
@NoException
public class vdsp implements LoadEnabled, InfoMapper {

    static { Loader.checkVersion("uncomplicate.javacpp", "accelerate"); }

    @Override public void init(ClassProperties properties) {
    }

    static InfoMap putTypedefHandle(InfoMap infoMap, String struct, String handle) {
        infoMap.put(new Info(handle).valueTypes(struct).pointerTypes(
                        "@ByPtrPtr " + handle, "@Cast(\"" + struct + "*\") PointerPointer"));
        return infoMap;
    }

    @Override public void map(InfoMap infoMap) {
        infoMap.put(new Info("_Nonnull", "_Nullable").cppTypes().annotations())
            .put(new Info("!0 && !0 && (__STDC_HOSTED__ == 1)").define(false))
            .put(new Info("vDSP_DFT_Interleaved_ComplextoComplex").skip())
            .put(new Info("vDSP_DFT_Interleaved_RealtoComplex").skip())
            .put(new Info("defined vDSP_DeprecateTranslations").define(false))
            .put(new Info("vDSP_fft3_zop", "vDSP_fft3_zopD", "vDSP_fft5_zop", "vDSP_fft5_zopD").skip());

        putTypedefHandle(infoMap, "OpaqueFFTSetup", "FFTSetup");
        putTypedefHandle(infoMap, "OpaqueFFTSetupD", "FFTSetupD");
        putTypedefHandle(infoMap, "vDSP_biquad_SetupStruct", "vDSP_biquad_Setup");
        putTypedefHandle(infoMap, "vDSP_biquad_SetupStructD", "vDSP_biquad_SetupD");
        putTypedefHandle(infoMap, "vDSP_biquadm_SetupStruct", "vDSP_biquadm_Setup");
        putTypedefHandle(infoMap, "vDSP_biquadm_SetupStructD", "vDSP_biquadm_SetupD");
        putTypedefHandle(infoMap, "vDSP_DFT_SetupStruct", "vDSP_DFT_Setup");
        putTypedefHandle(infoMap, "vDSP_DFT_SetupStructD", "vDSP_DFT_SetupD");
        putTypedefHandle(infoMap, "vDSP_DFT_Interleaved_SetupStruct", "vDSP_DFT_Interleaved_Setup");
        putTypedefHandle(infoMap, "vDSP_DFT_Interleaved_SetupStructD", "vDSP_DFT_Interleaved_SetupD");

    }


}
