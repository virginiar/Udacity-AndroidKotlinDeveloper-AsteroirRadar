package com.udacity.asteroidradar

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    val context = imageView.context
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
        context.getString(R.string.potentially_hazardous_asteroid_image)
            .also { imageView.contentDescription = it }
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
        context.getString(R.string.not_hazardous_asteroid_image)
            .also { imageView.contentDescription = it }
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    val context = imageView.context
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
        context.getString(R.string.potentially_hazardous_asteroid_image)
            .also { imageView.contentDescription = it }
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
        context.getString(R.string.not_hazardous_asteroid_image)
            .also { imageView.contentDescription = it }
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}

@BindingAdapter("imageUrl")
fun bindPictureOfDay(imageView: ImageView, picture: PictureOfDay?) {
    picture?.let {
        //val imgUri = picture.toUri().buildUpon().scheme("https").build()
        if (picture.mediaType == "image") {
            Picasso.with(imageView.context)
                .load(picture.url)
                .placeholder(R.drawable.placeholder_picture_of_day)
                .into(imageView)
            val description = String.format(
                imageView.context.getString(R.string.nasa_picture_of_day_content_description_format),
                picture.title
            )
            description.also { imageView.contentDescription = it }
        }

    }
}
