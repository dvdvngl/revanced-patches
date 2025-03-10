package app.revanced.shared.util.bytecode

import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.extensions.addInstruction
import app.revanced.patcher.extensions.replaceInstruction
import app.revanced.shared.util.integrations.Constants.UTILS_PATH
import org.jf.dexlib2.Opcode

internal object BytecodeHelper {

    fun injectInit(
        context: BytecodeContext,
        descriptor: String,
        methods: String
    ) {
        context.classes.forEach { classDef ->
            classDef.methods.forEach { method ->
                if (classDef.type.endsWith("WatchWhileActivity;") && method.name == "onCreate") {
                    val hookMethod =
                        context.proxy(classDef).mutableClass.methods.first { it.name == "onCreate" }

                    hookMethod.addInstruction(
                        2,
                        "invoke-static {}, $UTILS_PATH/$descriptor;->$methods()V"
                    )
                }
            }
        }
    }

    fun patchStatus(
        context: BytecodeContext,
        name: String
    ) {
        context.classes.forEach { classDef ->
            classDef.methods.forEach { method ->
                if (classDef.type.endsWith("PatchStatus;") && method.name == name) {
                    val patchStatusMethod =
                        context.proxy(classDef).mutableClass.methods.first { it.name == name }

                    patchStatusMethod.replaceInstruction(
                        0,
                        "const/4 v0, 0x1"
                    )
                }
            }
        }
    }
}