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
@Properties(inherit = javacpp.class, global = "uncomplicate.javacpp.accelerate.global.vimage", value = {
        @Platform(value = "macosx-arm64",
                  include = {"vImage_Types.h", "Alpha.h", "Convolution.h",
                      "Conversion.h", "Geometry.h", "Histogram.h", "Morphology.h",
                      "BasicImageTypes.h", "Transform.h"},
                  includepath = {"/Library/Developer/CommandLineTools/SDKs/MacOSX.sdk/System/Library/Frameworks/Accelerate.framework/Frameworks/vImage.framework/Headers/"
                      , "/Library/Developer/CommandLineTools/SDKs/MacOSX.sdk/usr/include/"})})
@NoException
public class vimage implements LoadEnabled, InfoMapper {

    static { Loader.checkVersion("uncomplicate.javacpp", "accelerate"); }

    @Override public void init(ClassProperties properties) {
    }

    static InfoMap putTypedefHandle(InfoMap infoMap, String struct, String handle) {
        infoMap.put(new Info(handle).valueTypes(struct)
                    .pointerTypes("@ByPtrPtr " + handle, "@Cast(\"" + struct + "*\") PointerPointer"));
        return infoMap;
    }

    static InfoMap putPixelHandle(InfoMap infoMap, String pixel, String struct, String arrayPointer) {
        infoMap.put(new Info(pixel)
                    .valueTypes("@Cast(\"" + struct + "*\") " + arrayPointer)
                    .pointerTypes("@Cast(\"" + pixel + "*\") PointerPointer"));
        infoMap.put(new Info("const " + pixel)
                    .valueTypes("@Cast(\"const " + struct + "*\") " + arrayPointer)
                    .pointerTypes("@Cast(\"const " + pixel + "*\") PointerPointer"));
        return infoMap;
    }

    @Override public void map(InfoMap infoMap) {
        infoMap
            .put(new Info("_Nonnull", "_Nullable", "VIMAGE_PF").cppTypes().annotations())
            .put(new Info("__has_extension(enumerator_attributes)").define(false))
            .put(new Info("Pixel_8")
                 .valueTypes("@Cast(\"uint8_t\") byte")
                 .pointerTypes("@Cast(\"Pixel_8*\") BytePointer"))
            .put(new Info("const Pixel_8")
                 .valueTypes("@Cast(\"const uint8_t\") byte")
                 .pointerTypes("@Cast(\"const Pixel_8*\") BytePointer"))

            .put(new Info("Pixel_F")
                 .valueTypes("@Cast(\"float\") float")
                 .pointerTypes("@Cast(\"Pixel_F*\") FloatPointer"))
            .put(new Info("const Pixel_F")
                 .valueTypes("@Cast(\"const float\") float")
                 .pointerTypes("@Cast(\"const Pixel_F*\") FloatPointer"))

            .put(new Info("vImage_Error").cast().valueTypes("long").pointerTypes("SizeTPointer"))
            .put(new Info("vImage_Flags").cast().valueTypes("long").pointerTypes("LongPointer"));

        putPixelHandle(infoMap, "Pixel_88", "uint8_t", "BytePointer");
        putPixelHandle(infoMap, "Pixel_8888", "uint8_t", "BytePointer");
        putPixelHandle(infoMap, "Pixel_FF", "float", "FloatPointer");
        putPixelHandle(infoMap, "Pixel_FFFF", "float", "FloatPointer");
        putPixelHandle(infoMap, "Pixel_ARGB_16U", "uint16_t", "ShortPointer");
        putPixelHandle(infoMap, "Pixel_ARGB_16F", "uint16_t", "ShortPointer");
        putPixelHandle(infoMap, "Pixel_ARGB_16S", "int16_t", "ShortPointer");
        putPixelHandle(infoMap, "Pixel_16F16F", "uint16_t", "ShortPointer");
        putPixelHandle(infoMap, "Pixel_16U16U", "uint16_t", "ShortPointer");
        putPixelHandle(infoMap, "Pixel_16S16S", "int16_t", "ShortPointer");

        putTypedefHandle(infoMap, "vImage_MultidimensionalTableData", "vImage_MultidimensionalTable");

    }


}
