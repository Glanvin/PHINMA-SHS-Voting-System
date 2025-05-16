package io.github.glavin.votingsystem.features.auth.domain

import chiro.composeapp.generated.resources.Res
import chiro.composeapp.generated.resources.admin
import chiro.composeapp.generated.resources.faculty
import chiro.composeapp.generated.resources.student
import chiro.composeapp.generated.resources.student_body_officer
import chiro.composeapp.generated.resources.student_strand_officer
import io.github.glavin.votingsystem.core.ui.UiText

enum class AccountType(val accountType: UiText) {
    STUDENT(UiText.StringResourceId(Res.string.student)),
    STRAND_OFFICER(UiText.StringResourceId(Res.string.student_strand_officer)),
    STUDENT_BODY_OFFICER(UiText.StringResourceId(Res.string.student_body_officer)),
    ADMIN(UiText.StringResourceId(Res.string.admin)),
    FACULTY(UiText.StringResourceId(Res.string.faculty));
}