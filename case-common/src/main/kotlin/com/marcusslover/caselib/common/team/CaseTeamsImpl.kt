/*
 * MIT License
 *
 * Copyright (c) marcusslover (MarcusSlover) <jr.marcusorlando@gmail.com>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.marcusslover.caselib.common.team

import com.marcusslover.caselib.common.util.CaseParent
import java.util.*

class CaseTeamManager : CaseParent<String, CaseTeam>() {
    fun createTeam(identifier: String): CaseTeam {
        val team = CaseTeamImpl(identifier)
        this.put(identifier, team)
        return team
    }
    fun removeTeam(team: CaseTeam) = this.remove(team.id())
    fun removeTeam(identifier: String) = this.remove(identifier)
    fun findById(identifier: String) = this.get(identifier)
    fun findByEntry(entry: UUID) = this.find { it.role(entry) != null }
    fun joinTeam(team: CaseTeam, entry: UUID, role: CaseRole) = team.role(entry, role)
    fun joinTeam(identifier: String, entry: UUID, role: CaseRole) = this.findById(identifier)?.role(entry, role)
    fun leaveTeam(entry: UUID) = this.findByEntry(entry)?.role(entry, null)
    fun leaveTeam(team: CaseTeam, entry: UUID) = team.role(entry, null)
    fun leaveTeam(identifier: String, entry: UUID) = this.findById(identifier)?.role(entry, null)
}

class CaseTeamImpl(private val identifier: String) : CaseParent<UUID, CaseRole>(), CaseTeam {
    private val roles: MutableList<CaseRole> = mutableListOf()
    override fun roles() = roles
    override fun role(entry: UUID, role: CaseRole?) = if (role != null) this.put(entry, role) else this.remove(entry)
    override fun role(entry: UUID) = this.get(entry)
    override fun id() = this.identifier

    /**
     * Check if the entry is the leader of the team.
     * @param entry Entry to check.
     * @return True if the entry is the leader of the team.
     */
    fun leader(entry: UUID) = this.role(entry) == CaseRoleImpl.LEADER
}

enum class CaseRoleImpl(private val weight: Int) : CaseRole {
    MEMBER(0),
    LEADER(1);

    override fun weight(): Int = this.weight
    override fun id() = this.name
}