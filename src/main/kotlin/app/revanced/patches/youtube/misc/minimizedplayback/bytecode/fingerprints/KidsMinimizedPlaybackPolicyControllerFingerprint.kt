package app.revanced.patches.youtube.misc.minimizedplayback.bytecode.fingerprints

import app.revanced.patcher.extensions.or
import app.revanced.patcher.fingerprint.method.impl.MethodFingerprint
import org.jf.dexlib2.AccessFlags
import org.jf.dexlib2.Opcode

object KidsMinimizedPlaybackPolicyControllerFingerprint : MethodFingerprint(
    returnType = "V",
    access = AccessFlags.PUBLIC or AccessFlags.FINAL,
    parameters = listOf("I", "L", "L"),
    opcodes = listOf(
        Opcode.IF_EQZ,
        Opcode.SGET_OBJECT,
        Opcode.IF_NE,
        Opcode.CONST_4,
        Opcode.IPUT_BOOLEAN,
        Opcode.IF_EQZ,
        Opcode.IGET,
        Opcode.INVOKE_STATIC
    ),
    customFingerprint = { it.definingClass.endsWith("MinimizedPlaybackPolicyController;") }
)
