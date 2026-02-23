package uz.gita.maxwayappclone.domain.repository

import uz.gita.maxwayappclone.domain.model.Branch

interface BranchRepository {
    suspend fun getBranches(): Result<List<Branch>>
}
