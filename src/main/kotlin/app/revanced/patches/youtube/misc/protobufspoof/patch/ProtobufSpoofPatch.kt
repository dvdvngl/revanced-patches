package app.revanced.patches.youtube.misc.protobufspoof.patch

import app.revanced.patcher.annotation.Description
import app.revanced.patcher.annotation.Name
import app.revanced.patcher.annotation.Version
import app.revanced.patcher.data.ResourceContext
import app.revanced.patcher.patch.PatchResult
import app.revanced.patcher.patch.PatchResultSuccess
import app.revanced.patcher.patch.ResourcePatch
import app.revanced.patcher.patch.annotations.DependsOn
import app.revanced.patcher.patch.annotations.Patch
import app.revanced.patches.youtube.misc.settings.resource.patch.SettingsPatch
import app.revanced.shared.annotation.YouTubeCompatibility
import app.revanced.shared.util.resources.ResourceHelper

@Patch
@Name("protobuf-spoof")
@Description("Spoofs the protobuf to prevent playback issues.")
@DependsOn(
    [
        ProtobufSpoofBytecodePatch::class,
        SettingsPatch::class
    ]
)
@YouTubeCompatibility
@Version("0.0.1")
class ProtobufSpoofPatch : ResourcePatch {
    override fun execute(context: ResourceContext): PatchResult {

        /*
         add settings
         */
        ResourceHelper.addSettings2(
            context,
            "PREFERENCE_CATEGORY: REVANCED_EXTENDED_SETTINGS",
            "PREFERENCE: EXTENDED_SETTINGS",
            "SETTINGS: EXPERIMENTAL_FLAGS",
            "SETTINGS: ENABLE_PROTOBUF_SPOOF"
        )

        ResourceHelper.patchSuccess(
            context,
            "protobuf-spoof"
        )

        return PatchResultSuccess()
    }
}