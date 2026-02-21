package uz.gita.maxwayappclone.data.mapper

import uz.gita.maxwayappclone.data.model.BranchDto
import uz.gita.maxwayappclone.domain.model.Branch

fun BranchDto.toDomain(): Branch = Branch(
    id = id,
    name = name,
    address = address,
    phone = phone,
    openTime = openTime,
    closeTime = closeTime,
    latitude = latitude,
    longitude = longitude
)

fun List<BranchDto>.toDomainList(): List<Branch> = map { it.toDomain() }
