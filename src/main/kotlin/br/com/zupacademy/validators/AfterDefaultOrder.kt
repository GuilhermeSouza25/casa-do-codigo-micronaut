package br.com.zupacademy.validators

import javax.validation.GroupSequence
import javax.validation.groups.Default

interface AfterDefaultOrder {}

@GroupSequence(Default::class, AfterDefaultOrder::class)
interface ValidationOrder

