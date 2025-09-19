Feature: 部门管理
  作为组织管理员
  我想要管理各部门
  以便有效地组织员工

  Scenario: 创建新部门
    Given 我有有效的部门信息，名称为"IT Department"，代码为"IT001"
    And id为"123e4567-e89b-12d3-a456-426614174000"的公司存在
    When 我创建一个新部门
    Then 部门应该被成功创建
    And 应该发布DepartmentCreatedEvent事件

  Scenario: 更新部门名称
    Given id为"123e4567-e89b-12d3-a456-426614174001"的部门存在
    When 我将部门名称更新为"Information Technology Department"
    Then 部门名称应该被更新
    And 应该发布DepartmentNameUpdatedEvent事件

  Scenario: 更新部门代码
    Given id为"123e4567-e89b-12d3-a456-426614174001"的部门存在
    When 我将部门代码更新为"IT002"
    Then 部门代码应该被更新
    And 应该发布DepartmentCodeUpdatedEvent事件

  Scenario: 激活部门
    Given id为"123e4567-e89b-12d3-a456-426614174001"的停用部门存在
    When 我激活该部门
    Then 部门应该被激活
    And 应该发布DepartmentActivatedEvent事件

  Scenario: 停用部门
    Given id为"123e4567-e89b-12d3-a456-426614174001"的活动部门存在
    And 该部门没有员工
    When 我停用该部门
    Then 部门应该被停用
    And 应该发布DepartmentDeactivatedEvent事件

  Scenario: 尝试停用有员工的部门失败
    Given id为"123e4567-e89b-12d3-a456-426614174001"的活动部门存在
    And 该部门有员工
    When 我尝试停用该部门
    Then 应该抛出异常，消息为"部门下有员工，无法停用"

  Scenario: 更新部门所属公司
    Given id为"123e4567-e89b-12d3-a456-426614174001"的部门存在
    And 该部门没有员工
    And id为"123e4567-e89b-12d3-a456-426614174002"的新公司存在
    When 我更新部门所属公司
    Then 部门所属公司应该被更新
    And 应该发布DepartmentCompanyUpdatedEvent事件

  Scenario: 当部门有员工时尝试更新部门所属公司失败
    Given id为"123e4567-e89b-12d3-a456-426614174001"的部门存在
    And 该部门有员工
    And id为"123e4567-e89b-12d3-a456-426614174002"的新公司存在
    When 我尝试更新部门所属公司
    Then 应该抛出异常，消息为"部门下有员工，无法更换公司"

  Scenario: 更新部门父部门
    Given id为"123e4567-e89b-12d3-a456-426614174001"的部门存在
    And id为"123e4567-e89b-12d3-a456-426614174003"的父部门存在
    When 我更新部门父部门
    Then 部门父部门应该被更新
    And 应该发布DepartmentParentUpdatedEvent事件

  Scenario: 尝试将部门设置为其自身的父部门失败
    Given id为"123e4567-e89b-12d3-a456-426614174001"的部门存在
    When 我尝试将该部门设置为其自身的父部门
    Then 应该抛出异常，消息为"不能将部门设置为其自身的父部门"