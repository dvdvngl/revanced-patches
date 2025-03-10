package app.revanced.patches.youtube.video.customspeed.bytecode.fingerprints

import app.revanced.patcher.fingerprint.method.impl.MethodFingerprint
import org.jf.dexlib2.Opcode

object VideoSpeedEntriesFingerprint : MethodFingerprint(
    opcodes = listOf(Opcode.FILL_ARRAY_DATA),
    customFingerprint = { methodDef ->
        methodDef.definingClass.endsWith("VideoSpeedEntries;") && methodDef.name == "<clinit>"
    }
)
