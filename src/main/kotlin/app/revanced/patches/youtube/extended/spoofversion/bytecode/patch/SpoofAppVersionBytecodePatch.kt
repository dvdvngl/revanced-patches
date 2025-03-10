package app.revanced.patches.youtube.extended.spoofversion.bytecode.patch

import app.revanced.patcher.annotation.Name
import app.revanced.patcher.annotation.Version
import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.extensions.addInstructions
import app.revanced.patcher.patch.BytecodePatch
import app.revanced.patcher.patch.PatchResult
import app.revanced.patcher.patch.PatchResultSuccess
import app.revanced.patches.youtube.extended.spoofversion.bytecode.fingerprints.AppVersionFingerprint
import app.revanced.shared.annotation.YouTubeCompatibility
import app.revanced.shared.extensions.toErrorResult
import app.revanced.shared.util.integrations.Constants.EXTENDED_PATH
import org.jf.dexlib2.iface.instruction.OneRegisterInstruction

@Name("spoof-app-version-bytecode-patch")
@YouTubeCompatibility
@Version("0.0.1")
class SpoofAppVersionBytecodePatch : BytecodePatch(
    listOf(
        AppVersionFingerprint
    )
) {
    override fun execute(context: BytecodeContext): PatchResult {

        AppVersionFingerprint.result?.let {
            val insertIndex = it.scanResult.patternScanResult!!.startIndex

            with (it.mutableMethod) {
                val register = (this.implementation!!.instructions[insertIndex] as OneRegisterInstruction).registerA
                addInstructions(
                    insertIndex + 1, """
                        invoke-static {v$register}, $EXTENDED_PATH/VersionOverridePatch;->getVersionOverride(Ljava/lang/String;)Ljava/lang/String;
                        move-result-object v$register
                    """
                )
            }
        } ?: return AppVersionFingerprint.toErrorResult()

        return PatchResultSuccess()
    }
}