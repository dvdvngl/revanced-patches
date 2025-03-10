package app.revanced.patches.youtube.layout.player.watermark.bytecode.patch

import app.revanced.patcher.annotation.Name
import app.revanced.patcher.annotation.Version
import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.extensions.addInstructions
import app.revanced.patcher.extensions.instruction
import app.revanced.patcher.extensions.removeInstruction
import app.revanced.patcher.fingerprint.method.impl.MethodFingerprint.Companion.resolve
import app.revanced.patcher.patch.BytecodePatch
import app.revanced.patcher.patch.PatchResult
import app.revanced.patcher.patch.PatchResultSuccess
import app.revanced.patches.youtube.layout.player.watermark.bytecode.fingerprints.HideWatermarkFingerprint
import app.revanced.patches.youtube.layout.player.watermark.bytecode.fingerprints.HideWatermarkParentFingerprint
import app.revanced.shared.annotation.YouTubeCompatibility
import app.revanced.shared.extensions.toErrorResult
import app.revanced.shared.util.integrations.Constants.PLAYER_LAYOUT
import org.jf.dexlib2.iface.instruction.TwoRegisterInstruction

@Name("hide-channel-watermark-bytecode-patch")
@YouTubeCompatibility
@Version("0.0.1")
class HideChannelWatermarkBytecodePatch : BytecodePatch(
    listOf(
        HideWatermarkParentFingerprint
    )
) {
    override fun execute(context: BytecodeContext): PatchResult {

        HideWatermarkParentFingerprint.result?.let { parentResult ->
            HideWatermarkFingerprint.also { it.resolve(context, parentResult.classDef) }.result?.let {
                val insertIndex = it.scanResult.patternScanResult!!.endIndex

                with (it.mutableMethod) {
                    val register = (instruction(insertIndex) as TwoRegisterInstruction).registerA
                    removeInstruction(insertIndex)
                    addInstructions(
                        insertIndex, """
                            invoke-static {}, $PLAYER_LAYOUT->hideChannelWatermark()Z
                            move-result v$register
                        """
                    )
                }
            } ?: return HideWatermarkFingerprint.toErrorResult()
        } ?: return HideWatermarkParentFingerprint.toErrorResult()

        return PatchResultSuccess()
    }
}
