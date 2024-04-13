@file:OptIn(org.jetbrains.compose.resources.InternalResourceApi::class)

package musicapp.composeapp.generated.resources

import kotlin.OptIn
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi

@ExperimentalResourceApi
private object Drawable0 {
  public val compose_multiplatform: DrawableResource =
      org.jetbrains.compose.resources.DrawableResource(
        "drawable:compose_multiplatform",
          setOf(
            org.jetbrains.compose.resources.ResourceItem(setOf(),
          "drawable/compose-multiplatform.xml"),
          )
      )
}

@ExperimentalResourceApi
internal val Res.drawable.compose_multiplatform: DrawableResource
  get() = Drawable0.compose_multiplatform
